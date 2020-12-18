package top.webra.utils.oos;

import com.qiniu.util.Auth;

public class QiniuyunUtil {

    public static void uploadImage(){
        String accessKey = "ObcystkEw1OIezPb0QAJCGSmMIObXOXETEVtcS9T";
        String secretKey = "YIW69ZyF1u_1HI877BNDpx7_TusX-McXkM0ap-Cc";
        String bucket = "webra";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
    }
}
