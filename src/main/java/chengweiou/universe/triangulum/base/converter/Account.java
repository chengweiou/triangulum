package chengweiou.universe.triangulum.base.converter;


import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements NotNullObj, Serializable {
    private Person person;
    private String extra;
    public static final Account NULL = new Account.Null();
    public static class Null extends Account implements NullObj {
        @Override
        public Person getPerson() { return Person.NULL; }
    }

}
