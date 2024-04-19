public class AccessManager {
    public static boolean hasAccess(String userType, ContentType contentKey) {
        if ("Educator".equals(userType) && ContentType.EDUCATOR.equals(contentKey)) {
            return true;
        }
        if("Student".equals(userType) && ContentType.STUDENT.equals(contentKey)) {
            return true;
        }
        if("Parent".equals(userType) && ContentType.PARENT.equals(contentKey)) {
            return true;
        }
        return false;
    }
    public enum ContentType {
        EDUCATOR, STUDENT, PARENT
    }
}