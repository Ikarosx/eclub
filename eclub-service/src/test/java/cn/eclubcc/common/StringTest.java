package cn.eclubcc.common;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author Ikaros
 * @date 2020/3/31 17:38
 */
public class StringTest {
    @Test
    public void subStringTest() {
        String str = StringUtils.substring("sadadasda3asd6as5d2asd663329", -6);
        System.out.println(str);
    }
}
