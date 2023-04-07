package weblux.jwt.models.auth.req_resp;

public class AuthReqResp<T> implements IAuthReqResp<T> {

    private final T data;
    private final String msg;

    public AuthReqResp(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public T getData() {
        return this.data;
    }
}
