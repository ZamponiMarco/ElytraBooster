package com.github.jummes.elytrabooster.portal.shape;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import lombok.Getter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Enumerable(classArray = {CircleShape.class, RectangleShape.class, TriangleShape.class, ComposedShape.class})
public abstract class Shape implements Model {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    protected char axis;

    protected List<Location> points;

    public Shape(char axis) {
        this.axis = axis;
    }

    public void setPoints(boolean isBlockOutline) {
        this.points = buildPoints(isBlockOutline);
    }

    public void clearPoints() {
        points.clear();
    }

    public abstract boolean isInPortalArea(Location location, double epsilon);

    protected abstract List<Location> buildPoints(boolean isBlockOutline);

    public abstract Location getCenterPoint();

}
