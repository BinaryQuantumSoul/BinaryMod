package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static com.quantumsoul.binarymod.util.WorldUtils.onBinDimLivingFall;

public class BugEntity extends MonsterEntity
{
    private static final BlockState defaultMimic = BlockInit.BUG_BLOCK.get().getDefaultState();
    private static final DataParameter<Optional<BlockState>> MIMIC = EntityDataManager.createKey(BugEntity.class, DataSerializers.OPTIONAL_BLOCK_STATE);

    public BugEntity(World worldIn)
    {
        this(EntityInit.BUG.get(), worldIn);
    }
    public BugEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    //=================================================== AI ===================================================
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1D, true));
        this.goalSelector.addGoal(2, new GroupGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new HideGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5D);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return onBinDimLivingFall(world, this.getPosition(), () -> super.onLivingFall(distance, damageMultiplier));
    }

    //=================================================== DATA ===================================================

    @Override
    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(MIMIC, Optional.of(defaultMimic));
    }

    public void setMimic(BlockState state)
    {
        if(state.isSolid() && state.getRenderType() != BlockRenderType.INVISIBLE)
            this.getDataManager().set(MIMIC, Optional.of(state));
        else
            this.getDataManager().set(MIMIC, Optional.of(defaultMimic));
    }

    public BlockState getMimic()
    {
        return this.getDataManager().get(MIMIC).orElse(defaultMimic);
    }

    //=================================================== SOUND ===================================================

    //=================================================== GOALS ===================================================
    protected class GroupGoal extends Goal
    {
        private final MobEntity entity;
        private final double moveSpeed;
        private MobEntity friend;
        private int timer;

        public GroupGoal(MobEntity entityIn, double speed)
        {
            this.entity = entityIn;
            this.moveSpeed = speed;
        }

        @Override
        public boolean shouldExecute()
        {
            List<MobEntity> list = entity.world.getEntitiesWithinAABB(entity.getClass(), entity.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            MobEntity mobEntity = null;
            double d0 = Double.MAX_VALUE;

            for (MobEntity tmp : list)
            {
                if(tmp != entity)
                {
                    double d1 = entity.getDistanceSq(tmp);
                    if (d1 <= d0)
                    {
                        d0 = d1;
                        mobEntity = tmp;
                    }
                }
            }

            if (mobEntity != null && d0 >= 2.0D)
            {
                friend = mobEntity;
                return true;
            }

            return false;
        }

        @Override
        public boolean shouldContinueExecuting()
        {
            if(friend.isAlive())
            {
                double d = entity.getDistanceSq(friend);
                return d >= 2.0D && d <= 256.0D;
            }

            return false;
        }

        @Override
        public void resetTask()
        {
            friend = null;
        }

        @Override
        public void tick()
        {
            if(timer-- <= 0)
            {
                timer = 10;
                entity.getNavigator().tryMoveToEntityLiving(friend, moveSpeed);
            }
        }
    }

    protected class HideGoal extends Goal
    {
        private final BugEntity entity;
        private long time;

        public HideGoal(BugEntity entityIn)
        {
            this.entity = entityIn;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean shouldExecute()
        {
            long i = entity.getEntityWorld().getGameTime();
            if (i - time > 50L)
            {
                time = i;
                return true;
            }

            return false;
        }

        @Override
        public void resetTask()
        {
            time = entity.getEntityWorld().getGameTime();
        }

        @Override
        public void tick()
        {
            entity.moveToBlockPosAndAngles(entity.getPosition(), 0.0F, 0.0F);
            entity.setMimic(entity.world.getBlockState(entity.getPosition().down()));
            entity.setRenderYawOffset(0.0F);
        }
    }
}
