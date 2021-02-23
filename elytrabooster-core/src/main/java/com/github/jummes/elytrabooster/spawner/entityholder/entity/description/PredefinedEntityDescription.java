package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.libs.annotation.Enumerable;

@Enumerable.Parent(classArray = {FireworkEntityDescription.class, MushroomEntityDescription.class,
        PotionEntityDescription.class})
@Enumerable.Displayable(name = "&c&lPredefined Styles", description = "gui.spawner.entityHolder.entityDescription.predefined.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTYzMzBhNGEyMmZmNTU4NzFmYzhjNjE4ZTQyMWEzNzczM2FjMWRjYWI5YzhlMWE0YmI3M2FlNjQ1YTRhNGUifX19")
public abstract class PredefinedEntityDescription extends EntityDescription {
}
