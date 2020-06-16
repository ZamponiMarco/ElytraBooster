package com.github.jummes.elytrabooster.portal.shape;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.util.ItemUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Enumerable.Parent(classArray = {CircleShape.class, RectangleShape.class, TriangleShape.class, ComposedShape.class})
public abstract class Shape implements Model {

    private static final String AXIS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBlNzViZTJmZWYxODNlOTAwMDljMGQyZTJkZmQ4MjliMjUwMzBmY2E0MTk5NTFlMzk1Y2E5NTYyZTY0N2U2MyJ9fX0=";
    private static final String X_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxZDZlZGE4M2VkMmMyNGRjZGNjYjFlMzNkZjM2OTRlZWUzOTdhNTcwMTIyNTViZmM1NmEzYzI0NGJjYzQ3NCJ9fX0=";
    private static final String Y_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlmZjhjNzQ0OTUwNzI5ZjU4Y2I0ZTY2ZGM2OGVhZjYyZDAxMDZmOGE1MzE1MjkxMzNiZWQxZDU1ZTMifX19";
    private static final String Z_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZjE4ZDQxNmY2OGU5YmQxOWQ1NWRmOWZhNzQyZWRmYmYxYTUyNWM4ZTI5ZjY1OWFlODMzYWYyMTdkNTM1In19fQ==";

    @Serializable(headTexture = AXIS_HEAD, description = "gui.portal.shape.axis", fromList = "axisList", fromListMapper = "axisListMapper")
    protected char axis;

    protected List<Location> points;

    public Shape(char axis) {
        this.axis = axis;
    }

    public static List<Object> axisList(ModelPath<?> path) {
        return Lists.newArrayList('x', 'y', 'z');
    }

    public static Function<Object, ItemStack> axisListMapper() {
        return obj -> {
            char c = (char) obj;
            ItemStack toReturn = new ItemStack(Material.BARRIER);
            String name = "";
            switch (c) {
                case 'x':
                    toReturn = Libs.getWrapper().skullFromValue(X_HEAD);
                    name = "&6&lX";
                    break;
                case 'y':
                    toReturn = Libs.getWrapper().skullFromValue(Y_HEAD);
                    name = "&6&lY";
                    break;
                case 'z':
                    toReturn = Libs.getWrapper().skullFromValue(Z_HEAD);
                    name = "&6&lZ";
            }
            return ItemUtils.getNamedItem(toReturn, name, new ArrayList<>());
        };
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
