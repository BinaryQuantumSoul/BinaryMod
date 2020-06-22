package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.BoolBlock;
import com.quantumsoul.binarymod.entity.BulletEntity;
import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShooterTileEntity extends UpgradableTileEntity implements IOnOffMachine
{
    private boolean on = false;
    private int timer = 0;
    private List<UUID> players = new ArrayList<>();

    public ShooterTileEntity()
    {
        super(TileEntityInit.SHOOTER.get(), 4);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        super.tick();

        if (!world.isRemote && on)
        {
            --timer;
            if (timer <= 0)
            {
                AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-15D, -15D, -15D), pos.add(15D, 15D, 15D));
                LivingEntity target = world.getClosestEntityWithinAABB(LivingEntity.class, EntityPredicate.DEFAULT, null, pos.getX(), pos.getY(), pos.getZ(),aabb);
                
                if (target != null && (!(target instanceof PlayerEntity) || !players.contains((target).getUniqueID())))
                {
                    BulletEntity bullet = new BulletEntity(world, pos.getX() + 0.5F, pos.getY() + 1F, pos.getZ() + 0.5F);

                    double dx = target.getPosX() - pos.getX() - 0.5F;
                    double dy = target.getPosY() - pos.getY();
                    double dz = target.getPosZ() - pos.getZ() - 0.5F;
                    double dxz = Math.sqrt(dx * dx + dz * dz);

                    double yaw = Math.atan2(dz, dx) - Math.PI / 2D;
                    double pitch = 0.5D * Math.asin(dxz * 0.05D) + Math.atan2(dy, dxz);
                    double x = -Math.sin(yaw) * Math.cos(pitch);
                    double y = Math.sin(pitch);
                    double z = Math.cos(yaw) * Math.cos(pitch);
                    LogManager.getLogger().debug(pitch * 180D / Math.PI);

                    bullet.shoot(x, y, z, 1, 0.0F);
                    world.addEntity(bullet);
                    
                    upgradeResets();
                }
            }
        }
    }

    @Override
    public void onOff()
    {
        on = !on;
        world.setBlockState(pos, getBlockState().with(BoolBlock.DONE, on), 2);
    }

    @Override
    protected void upgradeResets()
    {
        timer = (int) (40D * Math.pow(2, -level));
    }

    public void list(PlayerEntity player)
    {
        UUID id = player.getUniqueID();

        boolean removed = players.remove(id);
        if (!removed)
            players.add(id);

        player.sendMessage(new StringTextComponent(String.format(removed ? "removed %s": "added %s", id.toString())));
    }

    public boolean canUse(PlayerEntity player)
    {
        return players.isEmpty() || players.contains(player.getUniqueID());
    }

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putBoolean("on", on);
        compound.putInt("timer", timer);

        ListNBT list = new ListNBT();
        for (UUID uuid : players)
            list.add(NBTUtil.writeUniqueId(uuid));

        compound.put("players", list);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        on = compound.getBoolean("on");
        timer = compound.getInt("timer");

        ListNBT list = (ListNBT) compound.get("players");

        players = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            players.add(NBTUtil.readUniqueId(list.getCompound(i)));
    }
}
