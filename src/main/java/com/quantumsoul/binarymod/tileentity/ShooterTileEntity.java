package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.BoolBlock;
import com.quantumsoul.binarymod.entity.BulletEntity;
import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShooterTileEntity extends UpgradableTileEntity implements IOnOffMachine
{
    private final EntityPredicate target_predicate = new EntityPredicate().setCustomPredicate(e -> aimYawPitch(e)[1] >= -0.628F && (!(e instanceof PlayerEntity) || shouldShoot((PlayerEntity) e)));

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
                LivingEntity target = world.getClosestEntityWithinAABB(LivingEntity.class, target_predicate, null, pos.getX(), pos.getY(), pos.getZ(),aabb);
                
                if (target != null)
                {
                    double[] yawPitch = aimYawPitch(target);

                    double x = -Math.sin(yawPitch[0]) * Math.cos(yawPitch[1]);
                    double y = Math.sin(yawPitch[1]);
                    double z = Math.cos(yawPitch[0]) * Math.cos(yawPitch[1]);

                    if (Double.isFinite(x) && Double.isFinite(y) && Double.isFinite(z))
                    {
                        BulletEntity bullet = new BulletEntity(world, pos.getX() + 0.5F, pos.getY() + 1F, pos.getZ() + 0.5F);
                        bullet.shoot(x, y, z, 1, 0.0F);
                        world.addEntity(bullet);

                        upgradeResets();
                    }
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

        player.sendMessage(new TranslationTextComponent(removed ? "machine.binarymod.shooter_0": "machine.binarymod.shooter_1", player.getDisplayName()));
    }

    public boolean canUse(PlayerEntity player)
    {
        return players.isEmpty() || players.contains(player.getUniqueID());
    }

    public boolean shouldShoot(PlayerEntity player)
    {
        return !players.contains(player.getUniqueID());
    }

    public boolean isOff()
    {
        return !on;
    }

    protected double[] aimYawPitch(LivingEntity target)
    {
        double dist = Math.sqrt(target.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()));
        Vec3d motion = target.getMotion();

        double dx = target.getPosX() + motion.getX() * dist - pos.getX() - 0.5F;
        double dy = target.getPosY() - pos.getY() - 1.0F;
        double dz = target.getPosZ() + motion.getZ() * dist - pos.getZ() - 0.5F;
        double dxz = Math.sqrt(dx * dx + dz * dz);

        double yaw = Math.atan2(dz, dx) - Math.PI / 2D;
        double pitch = 0.5D * Math.asin(dxz * 0.05D) + Math.atan2(dy, dxz);

        return new double[]{yaw, pitch};
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
