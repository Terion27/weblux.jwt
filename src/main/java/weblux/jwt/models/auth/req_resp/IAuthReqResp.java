package weblux.jwt.models.auth.req_resp;

public interface IAuthReqResp<T> {
    String getMsg();

    T getData();
}
