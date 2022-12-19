package hello.lolRecord.common;

import java.util.Map;

public class MapdataUtil {

    public static Map setString(Map pDoc,String strKey,String strValue) {
      pDoc.put(strKey,strValue);
      return pDoc;
    }
    public static String getString(Map pDoc,String strKey) {
        String strValue = pDoc.get(strKey).toString();
        return strValue;
    }
    public static Map setInt(Map pDoc,String strKey,int strValue) {
        pDoc.put(strKey,strValue);
        return pDoc;
    }
    public static int getInt(Map pDoc,String strKey) {
        int strValue = (int) pDoc.get(strKey);
        return strValue;
    }
    public static Map setMap(Map pDoc,String strKey,Object obValue) {
        pDoc.put(strKey,obValue);
        return pDoc;
    }
    public static Object getMap(Map pDoc,String strKey) {
        Object obValue = pDoc.get(strKey);
        return obValue;
    }


}
