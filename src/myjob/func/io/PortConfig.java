/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.io;

/**
 *
 * @author guillermot
 */
public class PortConfig {
        protected Integer baudRate = 9600;
        protected Integer dataBits = 7;
        protected Integer stopBits = 1;
        protected Integer parity = 0;
        private boolean setted = false;
        String portName = "/dev/ttyS0";


    public PortConfig() {

    }

    public PortConfig(String portName, Integer baudRate, Integer dataBits, Integer stopBits, Integer parity) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        setted = true;
    }

    public boolean isSetted() {
        return setted;
    }

    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        setted = true;
        this.baudRate = baudRate;
    }

    public Integer getDataBits() {
        return dataBits;
    }

    public void setDataBits(Integer dataBits) {
        setted = true;
        this.dataBits = dataBits;
    }

    public Integer getParity() {
        return parity;
    }

    public void setParity(Integer parity) {
        setted = true;
        this.parity = parity;
    }

    public Integer getStopBits() {
        return stopBits;
    }

    public void setStopBits(Integer stopBits) {
        setted = true;
        this.stopBits = stopBits;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        setted = true;
        this.portName = portName;
    }

    @Override
    public String toString() {
        return "PortConfig{" + "baudRate=" + baudRate + ", dataBits=" + dataBits + ", stopBits=" + stopBits + ", parity=" + parity + ", setted=" + setted + ", portName=" + portName + '}';
    }
    
}
