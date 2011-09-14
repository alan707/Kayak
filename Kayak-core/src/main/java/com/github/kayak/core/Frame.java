/**
 *      This file is part of Kayak.
 *
 *      Kayak is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      Kayak is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Kayak.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.kayak.core;

import java.util.Comparator;

/**
 * A frame is a atomic unit of data on a CAN bus. It contains the raw data and is
 * identified by the identifier. Optionally a timestamp can be added which
 * represents the time when the frame was received. The BusName indicates from
 * which bus this frame was received.
 *
 * @author Jan-Niklas Meier <dschanoeh@googlemail.com>
 */
public class Frame {

    private byte[] data;
    private int identifier;
    private long timestamp;
    private Bus bus;

    public static class IdentifierComparator implements Comparator<Frame> {

        @Override
        public int compare(Frame f1, Frame f2) {
            if(f1.equals(f2) || f1.getIdentifier() == f2.getIdentifier())
                return 0;

            if(f1.getIdentifier() < f2.getIdentifier())
                return -1;

            return 1;
        }

    };

    public static class TimestampComparator implements Comparator<Frame> {

        @Override
        public int compare(Frame f1, Frame f2) {
            if(f1.equals(f2) || f1.getTimestamp() == f2.getTimestamp())
                return 0;

            if(f1.getTimestamp() < f2.getTimestamp())
                return -1;

            return 1;
        }

    };

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Frame() {

    }

    public Frame(int identifier, byte[] data) {
        this.identifier = identifier;
        this.data = data;
    }

    public Frame(int identifier, byte[] data, long timestamp) {
        this.identifier = identifier;
        this.data = data;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getLength() {
        return data.length;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        String s = "Frame [" + Integer.toHexString(identifier) + "] " + Util.byteArrayToHexString(data);
        return s;
    }

    public String toLogFileNotation() {
        StringBuilder sb = new StringBuilder(40);

        sb.append('(');
        sb.append(Long.toString(timestamp/1000000));
        sb.append('.');
        sb.append(String.format("%06d",timestamp%1000000));
        sb.append(") ");
        sb.append(bus.getName());
        sb.append(" ");
        if(isExtendedIdentifier()) {
            sb.append(String.format("%08x", identifier));
        } else {
            sb.append(String.format("%03x", identifier));
        }
        sb.append('#');
        for(byte b : data) {
            sb.append(String.format("%02x", b));
        }
        sb.append('\n');
        return sb.toString();
    }

    public boolean isExtendedIdentifier() {
        return (identifier > 2048);
    }
}
