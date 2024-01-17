package pl.webd.dawid124.ioengine.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.request.WriteSingleRegisterRequest;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.settings.ModbusSettings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Service
public class ModbusTcpService {
    private static final Logger LOG = LogManager.getLogger(ModbusTcpService.class );
    private final ModbusSettings modbusSettings;

    public ModbusTcpService(ModbusSettings modbusSettings) {
        this.modbusSettings = modbusSettings;
    }

//    public static void main(String[] argv) {
//        ModbusSettings s = new ModbusSettings();
//        s.setHost("192.168.0.10");
//        ModbusTcpService service = new ModbusTcpService(s);
//        service.init();
//
////        LOG.info("WORK_MODL " + service.readRegister(1, 4));
////        service.writeRegister(1, 4, 1);
////        LOG.info("WORK_MODL " + service.readRegister(1, 4));
//
//        LOG.info("Mode_PARTY " + service.readRegister(1, 34));
//        service.writeRegister(1, 34, 1); // party mode
//        LOG.info("Mode_PARTY " + service.readRegister(1, 34));
//        service.writeRegister(1, 34, 0); // party mode
//        LOG.info("Mode_PARTY " + service.readRegister(1, 34));
//
//
//        service.writeRegister(1, 4, 3); // user mode  , 1 – Postój, 3 – User1, 4 – User2, 5 – User3, 6 – User4
//        service.writeRegister(1, 32, 0); // Mode_WIND mode
//        service.writeRegister(1, 34, 0); // party mode
//        service.writeRegister(1, 37, 0); // scheduler mode
//
//
//    }

    private ModbusMaster createModbusTcp() {
        try {
            TcpParameters tcpParameters = new TcpParameters();
            tcpParameters.setHost(InetAddress.getByName(modbusSettings.getHost()));
            tcpParameters.setKeepAlive(true);
            tcpParameters.setPort(Modbus.TCP_PORT);

            ModbusMaster modbusMasterTCP = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            modbusMasterTCP.setResponseTimeout(5000);
            return modbusMasterTCP;

//            Modbus.setAutoIncrementTransactionId(true);
        } catch (UnknownHostException e) {
            LOG.error("Error on modbus connecting on host: {}", modbusSettings.getHost(), e);
            throw new RuntimeException(e);
        }
    }

    public int readRegister(int slaveId, int offset) {
        ModbusMaster modbusTcp = createModbusTcp();
        try {
            if (!modbusTcp.isConnected()) {
                modbusTcp.connect();
            }

            int[] registerValues = modbusTcp.readHoldingRegisters(slaveId, offset, 1);

            return registerValues[0];
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOG.error("Error on modbus reading on host: [{}], slaveId: [{}], offset: [{}]",
                    modbusSettings.getHost(), slaveId, offset, e);
        } finally {
            try {
                modbusTcp.disconnect();
            } catch (ModbusIOException e) {
                LOG.error("Error on modbus disconnecting on host: {}", modbusSettings.getHost(), e);
            }
        }

        return -1;
    }

    public void writeRegister(int slaveId, int offset, int value) {
        ModbusMaster modbusTcp = createModbusTcp();
        try {
            if (!modbusTcp.isConnected()) {
                modbusTcp.connect();
            }

            WriteSingleRegisterRequest req = new WriteSingleRegisterRequest();
            req.setServerAddress(slaveId);
            req.setStartAddress(offset);
            req.setValue(value);
            req.setProtocolId(0);

            modbusTcp.setTransactionId(1);
            modbusTcp.processRequest(req);

        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            LOG.error("Error on modbus writing on host: [{}], slaveId: [{}], offset: [{}]",
                    modbusSettings.getHost(), slaveId, offset, e);
        } finally {
            try {
                modbusTcp.disconnect();
            } catch (ModbusIOException e) {
                LOG.error("Error on modbus disconnecting on host: {}", modbusSettings.getHost(), e);
            }
        }
    }

    private static short getWriteValue(int value) {
        byte[] values = ByteBuffer.allocate(4).putInt(value).array();
        values = Arrays.copyOfRange(values, 2, 4);
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.put(values[0]);
        bb.put(values[1]);
        return bb.getShort(0);
    }
}
