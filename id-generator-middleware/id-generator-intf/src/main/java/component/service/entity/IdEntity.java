package component.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdEntity implements Serializable {
    private long machine;
    private long seq;
    private long time;
    private long genMethod;
    private long type;
    private long version;
}
