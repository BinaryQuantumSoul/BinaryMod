package com.quantumsoul.binarymod.entity;

import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTables;
import org.apache.logging.log4j.LogManager;

import static com.quantumsoul.binarymod.util.WorldUtils.onBinDimLivingFall;

public class WormEntity extends MonsterEntity
{
    private static final DataParameter<Boolean> DUPLICATE = EntityDataManager.createKey(WormEntity.class, DataSerializers.BOOLEAN);

    public WormEntity(World worldIn)
    {
        this(EntityInit.WORM.get(), worldIn);
    }
    public WormEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    //=================================================== AI ===================================================
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return onBinDimLivingFall(world, this.getPosition(), () -> super.onLivingFall(distance, damageMultiplier));
    }

    @Override
    public void remove(boolean keepData)
    {
        if (!world.isRemote && this.getDuplicate() && this.getHealth() <= 0.0F && !this.removed)
        {
            double x = rand.nextBoolean() ? 0.5D : -0.5D;
            double z = rand.nextBoolean() ? 0.5D : -0.5D;
            WormEntity entity = (WormEntity) this.getType().create(world);

            if (this.hasCustomName())
                entity.setCustomName(this.getCustomName());
            if (this.isNoDespawnRequired())
                entity.enablePersistence();
            entity.setInvulnerable(this.isInvulnerable());

            entity.setSecond();
            entity.setLocationAndAngles(getPosX() + x, getPosY() + 0.5D, getPosZ() + z, rand.nextFloat() * 360.0F, 0.0F);
            world.addEntity(entity);
        }

        super.remove(keepData);
    }

    @Override
    protected boolean canDropLoot()
    {
        return !this.getDuplicate();
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.canDropLoot() ? this.getType().getLootTable() : LootTables.EMPTY;
    }

    //=================================================== DATA ===================================================
    @Override
    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(DUPLICATE, rand.nextInt(3) == 0);
    }

    public boolean getDuplicate()
    {
        return this.getDataManager().get(DUPLICATE);
    }

    public void setSecond()
    {
        this.getDataManager().set(DUPLICATE, false);
    }
}
