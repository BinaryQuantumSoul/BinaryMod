package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

import static com.quantumsoul.binarymod.util.WorldUtils.isOnGround;

public class FlyerEntity extends Entity
{
    public FlyerEntity(World worldIn)
    {
        this(EntityInit.FLYER.get(), worldIn);
    }
    public FlyerEntity(EntityType<?> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        preventEntitySpawning = true;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return getBoundingBox();
    }

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand)
    {
        if (player.isCrouching())
            return false;
        else
            return !world.isRemote && player.startRiding(this);
    }

    @Override
    public boolean hitByEntity(Entity entityIn)
    {
        if (entityIn instanceof PlayerEntity)
        {
            if (!((PlayerEntity) entityIn).abilities.isCreativeMode)
                entityDropItem(new ItemStack(ItemInit.FLYER.get()));

            remove();
            return true;
        }

        return false;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (canPassengerSteer())
        {
            setMotion(getMotion().scale(0.5F).add(0D, -0.075D, 0D));

            if (getControllingPassenger() instanceof LivingEntity)
            {
                LivingEntity controller = (LivingEntity) getControllingPassenger();

                rotationYaw = controller.rotationYaw;
                rotationPitch = MathHelper.clamp(-controller.rotationPitch, -45F, 45F);
                double radYaw = rotationYaw * Math.PI / 180D;
                double radPitch = rotationPitch * Math.PI / 180D;

                float move = controller.moveForward / 2F;
                setMotion(getMotion().add(-Math.sin(radYaw) * move, Math.sin(radPitch) * move, Math.cos(radYaw) * move));

                move(MoverType.SELF, getMotion());
            }

            if (isOnGround(this))
                rotationPitch = 0F;
        }
        else
        {
            setMotion(Vec3d.ZERO);
            move(MoverType.SELF, getMotion());
        }
    }

    @Override
    protected boolean canBeRidden(Entity entityIn)
    {
        return true;
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        if (isPassenger(passenger))
        {
            passenger.setPosition(getPosX(), getPosY() - 0.45F, getPosZ());
            passenger.setMotion(getMotion());
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return false;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger()
    {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void registerData()
    {}

    @Override
    protected void readAdditional(CompoundNBT compound)
    {}

    @Override
    protected void writeAdditional(CompoundNBT compound)
    {}

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
