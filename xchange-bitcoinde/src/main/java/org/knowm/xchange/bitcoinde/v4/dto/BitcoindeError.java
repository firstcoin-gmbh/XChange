package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class BitcoindeError implements Serializable {

  /** */
  private static final long serialVersionUID = -4819019431808187140L;

  private final String message;
  private final String code;
  private final String field;

  public BitcoindeError(
      @JsonProperty("message") String message,
      @JsonProperty("code") String code,
      @JsonProperty("field") String field) {
    this.message = message;
    this.code = code;
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }

  public String getField() {
    return field;
  }

  @Override
  public String toString() {
    return "BitcoindeError["
        + "message='"
        + message
        + '\''
        + ", code='"
        + code
        + '\''
        + ", field='"
        + field
        + '\''
        + "]";
  }
}
