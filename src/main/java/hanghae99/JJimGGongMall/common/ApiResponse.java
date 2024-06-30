package hanghae99.JJimGGongMall.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private HttpHeaders headers;
    private String message;
    private T data;

    @Builder
    private ApiResponse(HttpStatus status, HttpHeaders headers, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.headers = headers;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, HttpHeaders headers, String message, T data) {
        return new ApiResponse<>(httpStatus, headers, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, null, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus) {
        return of(httpStatus, null);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, HttpHeaders headers, T data) {
        return new ApiResponse<>(httpStatus, headers, httpStatus.name(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> create() {
        return of(HttpStatus.CREATED);
    }


}
