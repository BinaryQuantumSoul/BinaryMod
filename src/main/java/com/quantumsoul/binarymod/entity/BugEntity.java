package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class BugEntity extends MonsterEntity
{
    public static final BlockState defaultMimic = BlockInit.BUG_BLOCK.get().getDefaultState();
    public BlockState mimic;

    public BugEntity(World worldIn)
    {
        this(EntityInit.BUG.get(), worldIn);
    }
    public BugEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
        mimic = defaultMimic;
    }

    //=================================================== AI ===================================================
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2D, true));
        this.goalSelector.addGoal(2, new GroupGoal(this, 1D));
        this.goalSelector.addGoal(3, new HideGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D);
    }

    //=================================================== SOUND =================================================== todo


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
            if (entity.getPosX() % 1 != 0.0D || entity.getPosY() % 1 != 0.0D || entity.getPosZ() % 1 != 0.0D || entity.rotationYaw != 0.0F || entity.rotationPitch != 0F || entity.rotationYawHead != 0.0F)
            {
                entity.moveToBlockPosAndAngles(entity.getPosition(), 0.0F, 0.0F);
                entity.setRotationYawHead(0.0F);
                BlockPos pos = entity.getPosition().down();
                BlockState state = entity.world.getBlockState(pos);
                entity.mimic = state.isSolid() && state.getShape(entity.world, pos) == VoxelShapes.fullCube() ? state : defaultMimic;
            }
        }
    }
}
