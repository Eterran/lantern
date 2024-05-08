package GUI;

public class AccessManager {
    public static boolean hasAccess(String userType, ContentType contentKey) {
        if ("educator".equals(userType) && ContentType.EDUCATOR.equals(contentKey)) {
            return true;
        }
        if("student".equals(userType) && ContentType.STUDENT.equals(contentKey)) {
            return true;
        }
        if("parent".equals(userType) && ContentType.PARENT.equals(contentKey)) {
            return true;
        }
        return false;
    }
    public enum ContentType {
        EDUCATOR, STUDENT, PARENT
    }
}