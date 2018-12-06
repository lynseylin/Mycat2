package io.mycat.mycat2.packet;

import io.mycat.mycat2.testTool.TestUtil;
import io.mycat.mysql.packet.ErrorPacket;
import io.mycat.proxy.ProxyBuffer;
import io.mycat.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static io.mycat.mycat2.testTool.TestUtil.ofBuffer;

/**
 * Created by linxiaofang on 2018/11/23.
 */
public class ErrorPacketTest {
    @Test
    public void testErrorPacket() {
        int[][] errPkts = new int[][] {
            { 0x29, 0x00, 0x00, 0x01, 0xff, 0x7a, 0x04, 0x23, 0x34, 0x32, 0x53, 0x30, 0x32, 0x54, 0x61, 0x62, 0x6c,
              0x65, 0x20, 0x27, 0x74, 0x65, 0x73, 0x74, 0x2e, 0x74, 0x65, 0x73, 0x74, 0x32, 0x27, 0x20, 0x64,
              0x6f, 0x65, 0x73, 0x6e, 0x27, 0x74, 0x20, 0x65, 0x78, 0x69, 0x73, 0x74 },
            { 0x17,0x00,0x00,0x01,0xff,0x48,0x04,0x23,0x48,0x59,0x30,0x30,0x30,0x4e,0x6f,0x20,       /* .....H.#HY000No tables used */
              0x74,0x61,0x62,0x6c,0x65,0x73,0x20,0x75,0x73,0x65,0x64 }
        };

        for (int i=0; i < errPkts.length; i++) {
            int[] errPkt = errPkts[i];
            ErrorPacket errorPacket = new ErrorPacket();
            errorPacket.read(ofBuffer(errPkt));

            ProxyBuffer buffer = new ProxyBuffer(ByteBuffer.allocate(errPkt.length));
            errorPacket.write(buffer);
            byte[] array = buffer.getBuffer().array();
            final String hexs = StringUtil.dumpAsHex(array);
            System.out.println(hexs);
            int[] ints = Arrays.copyOf(errPkt, errPkt.length);
            Assert.assertArrayEquals(TestUtil.of(ints),array);
            Assert.assertEquals(errorPacket.calcPacketSize()+4, errPkt.length);
        }
    }
}
