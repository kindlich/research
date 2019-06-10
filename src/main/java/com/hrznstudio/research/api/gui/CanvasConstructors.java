package com.hrznstudio.research.api.gui;

import com.hrznstudio.research.ResearchMod;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CanvasConstructors {

    private static final Set<ConOverride<?, ?>> overrides = new HashSet<>();

    //static {
    //    registerOverride(new ConOverride<>(CanvasText.class, CanvasTextSpecial.class, "I am Special!!!"));
    //    registerOverride(new ConOverride<>(CanvasText.class, CanvasTextSpecial.class, "I am very Special!!!"));
    //}

    @Contract(pure = true)
    private CanvasConstructors() {
    }

    public static void registerOverride(ConOverride<?, ?> conOverride) {
        overrides.removeIf(override -> override.overrides(conOverride.overridden));
        overrides.add(conOverride);
    }

    public static CanvasConstructor<CanvasSimple> getBasic() {
        //return CanvasSimple::new;
        return getConstructor(CanvasSimple.class);
    }

    public static CanvasConstructor<CanvasBorder> getBorder(int borderSize, int borderColor) {
        return getConstructor(CanvasBorder.class, borderColor, borderSize);
    }

    public static CanvasConstructor<CanvasText> getText(String text, int color) {
        //return (pane, offsetX, offsetY, width, height) -> new CanvasText(pane, offsetX, offsetY, width, height, text, color);
        return getConstructor(CanvasText.class, text, color);
    }

    public static CanvasConstructor<CanvasFilled> getFilled(int color) {
        //return (pane, offsetX, offsetY, width, height) -> new CanvasFilled(pane, offsetX, offsetY, width, height, color);
        return getConstructor(CanvasFilled.class, color);
    }

    public static CanvasConstructor<CanvasImage> getImage(ResourceLocation location) {
        return getConstructor(CanvasImage.class, location);
    }

    @Contract(pure = true)
    private static Class<?> makePrimitive(Class<?> type) {
        if (type == Integer.class)
            return int.class;
        if (type == Float.class)
            return float.class;
        if (type == Double.class)
            return double.class;
        if (type == Byte.class)
            return byte.class;
        if (type == Boolean.class)
            return boolean.class;
        if (type == Character.class)
            return char.class;
        if (type == Long.class)
            return long.class;
        return type;
    }

    public static <T extends Canvas> CanvasConstructor<T> getConstructor(Class<T> cls, Object... additionalParameters) {
        final Class<?>[] typeParameters = new Class<?>[additionalParameters.length + 3];
        typeParameters[0] = Canvas.class;
        typeParameters[1] = double.class;//width
        typeParameters[2] = double.class;//height

        for (int i = 0; i < additionalParameters.length; i++) {
            typeParameters[i + 3] = makePrimitive(additionalParameters[i].getClass());
        }
        return getConstructorFullTypes(cls, additionalParameters, typeParameters);
    }

    public static <T extends Canvas> CanvasConstructor<T> getConstructorTypes(Class<T> cls, Object[] additionalParameters, Class<?>... additionalParameterTypes) {
        final Class<?>[] types = new Class[additionalParameterTypes.length + 3];
        types[0] = Canvas.class;
        types[1] = double.class;
        types[2] = double.class;
        System.arraycopy(additionalParameterTypes, 0, types, 3, additionalParameterTypes.length);
        return getConstructorFullTypes(cls, additionalParameters, types);
    }

    @Contract(pure = true)
    public static <T extends Canvas> CanvasConstructor<T> getConstructorFullTypes(Class<? extends T> cls, Object[] additionalParameters, Class<?>... typeParameters) {
        final ConOverride<? extends T, ?> override = getOverride(cls);

        final Object[] usedAdditionalParameters;
        final Class<? extends T> usedClass;
        final Class<?>[] usedTypeParameters;


        if (override != null) {
            usedClass = override.getSubstitute();

            final Object[] overrideAdditionalArguments = override.getAdditionalArguments();
            usedAdditionalParameters = new Object[additionalParameters.length + overrideAdditionalArguments.length];
            System.arraycopy(additionalParameters, 0, usedAdditionalParameters, 0, additionalParameters.length);
            System.arraycopy(overrideAdditionalArguments, 0, usedAdditionalParameters, additionalParameters.length, overrideAdditionalArguments.length);

            final Class<?>[] overrideAdditionalTypeParameters = override.getAdditionalTypeParameters();
            usedTypeParameters = new Class[typeParameters.length + overrideAdditionalTypeParameters.length];
            System.arraycopy(typeParameters, 0, usedTypeParameters, 0, typeParameters.length);
            System.arraycopy(overrideAdditionalTypeParameters, 0, usedTypeParameters, typeParameters.length, overrideAdditionalTypeParameters.length);

        } else {
            usedClass = cls;
            usedAdditionalParameters = additionalParameters;
            usedTypeParameters = typeParameters;
        }

        if (typeParameters.length != additionalParameters.length) {
            if (typeParameters.length != additionalParameters.length + 3)
                throw new IllegalStateException();

        }

        if(usedClass.getEnclosingClass() != null && !Modifier.isStatic(usedClass.getModifiers()))
            throw new IllegalArgumentException("Currently no non-static inner classes are supported!");


        return (parent, width, height) -> {
            try {
                final Object[] p = new Object[usedTypeParameters.length];
                p[0] = parent;
                p[1] = width;
                p[2] = height;
                System.arraycopy(usedAdditionalParameters, 0, p, 3, usedAdditionalParameters.length);

                return usedClass.getDeclaredConstructor(usedTypeParameters).newInstance(p);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                ResearchMod.LOGGER.catching(Level.ERROR, e);
            }
            return null;
        };
    }

    @Contract(pure = true)
    @Nullable
    private static <T extends Canvas> ConOverride<? extends T, ?> getOverride(Class<T> cls) {
        for (ConOverride<?, ?> override : overrides) {
            if (override.overrides(cls))
                //noinspection unchecked
                return (ConOverride<? extends T, ?>) override;
        }
        return null;
    }

    public static final class ConOverride<T extends Canvas, TNew extends T> {
        private final Class<T> overridden;
        private final Class<TNew> substitute;
        private final Object[] additionalArguments;
        private final Class<?>[] additionalArgumentTypes;

        @Contract(pure = true)
        public ConOverride(Class<T> overridden, Class<TNew> substitute, Object... additionalArguments) {
            this.overridden = overridden;
            this.substitute = substitute;
            this.additionalArguments = additionalArguments;
            this.additionalArgumentTypes = new Class[additionalArguments.length];
            for (int i = 0; i < additionalArguments.length; i++) {
                additionalArgumentTypes[i] = makePrimitive(additionalArguments[i].getClass());
            }
        }

        @Contract(pure = true)
        public ConOverride(Class<T> overridden, Class<TNew> substitute, Object[] additionalArguments, Class<?>[] additionalArgumentTypes) {
            this.overridden = overridden;
            this.substitute = substitute;
            this.additionalArguments = additionalArguments;
            this.additionalArgumentTypes = additionalArgumentTypes;
        }

        @Contract(pure = true)
        private boolean overrides(Class<?> clazz) {
            return overridden == clazz;
        }

        @Contract(pure = true)
        private Class<TNew> getSubstitute() {
            return substitute;
        }

        @Contract(pure = true)
        private Object[] getAdditionalArguments() {
            return additionalArguments;
        }

        @Contract(pure = true)
        private Class<?>[] getAdditionalTypeParameters() {
            return additionalArgumentTypes;
        }
    }

}
