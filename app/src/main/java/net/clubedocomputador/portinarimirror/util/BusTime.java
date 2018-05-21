package net.clubedocomputador.portinarimirror.util;

import android.graphics.Color;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pedromalta on 13/09/17.
 */

public class BusTime {

    private final String time;
    private final Boolean next;
    private final Color color;

    public BusTime(String time, Boolean next, Color color){
        this.time = time; this.next = next; this.color = color;
    }

    public String getTime() {
        return time;
    }

    public Color getColor() {
        return color;
    }

    public Boolean isNext() {
        return next;
    }

    private static final int[] weekDays = {
            300, 315, 350,
            385,
            420, 450,
            480, 510,
            545, 585,
            625,
            665, 705,
            745,
            785, 825,
            865,
            905, 945,
            985,
            1025, 1065,
            1095, 1125,
            1155, 1185,
            1220, 1255,
            1290,
            1325, 1365,
            1400,
            1440, //24:00 em vez de 00:00
    };

    private static final String[] dayOffs = {
            "05:00",
            "05:15",
            "05:55",
            "06:35",
            "07:15",
            "07:55",
            "08:35",
            "09:15",
            "09:55",
            "10:35",
            "11:15",
            "11:55",
            "12:35",
            "13:15",
            "13:55",
            "14:35",
            "15:15",
            "15:55",
            "16:35",
            "17:15",
            "17:55",
            "18:35",
            "19:15",
            "19:55",
            "20:35",
            "21:15",
            "22:00",
            "23:00",
            "00:00"
    };

    public static BusTime[] getSchedule(int quantity) {
        Calendar date = Calendar.getInstance();

        boolean weekend = (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        int now = hour * 60 + minute;
        int half = quantity / 2;
        int next = 0;

        Queue<BusTime> busTimes = new LinkedList<BusTime>();

        for (int i = 0; i < weekDays.length; i++){
            String time = String.format("%02d", weekDays[i] / 60) + ":" + String.format("%02d", weekDays[i] % 60);

            busTimes.add(new BusTime(time, weekDays[i] > now && i-1 >= 0 &&  weekDays[i-1] < now, null));

            if (busTimes.size() > quantity)
                busTimes.remove();

            if (weekDays[i] > now)
                next++;

            if (next-(half/2) == half)
                break;
        }

        return busTimes.toArray(new BusTime[busTimes.size()]);
    }


}
