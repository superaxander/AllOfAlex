package alexanders.mods.aoa;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Util {
    private static String secureRandomState = null;

    public static <E extends Enum, T extends Enum & Cycleable<E>> E cycleEnum(T e) {
        return e.vals()[(e.ordinal() + 1) % e.vals().length];
    }

    public static String getSecureRandomState() {
        if (secureRandomState == null) {
            try {
                SecureRandom r = SecureRandom.getInstanceStrong();
                byte[] bytes = new byte[64];
                r.nextBytes(bytes);
                return secureRandomState = Base64.getEncoder().encodeToString(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        } else {
            return secureRandomState;
        }
    }
}
