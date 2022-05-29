package com.pulsecheck;

import java.util.*;
import java.util.Map.Entry;

public class Main {
    static LinkedHashMap<Long, Double> RR;
    static Double NR;
    static Long[][] TA;
    static int ti;
    static int bi;
    static Long[][] BA;
    static LinkedHashMap<Long, Double> TAi;
    static LinkedHashMap<Long, Double> BAi;
    static boolean isTA;
    static boolean isBA;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите нормальную длительность интервала NR:");
        NR = in.nextDouble();
        System.out.println("Введите количество зарегестрированных импульсов:");
        int N = in.nextInt();
        RR = new LinkedHashMap<>();
        System.out.println("Введите время регистрации и длительность импульсов:");

        for (int i = 0; i<N; i++) {
            long time = in.nextLong();
            double dur = in.nextDouble();

            RR.put(time, dur);
        }

        System.out.println("\nПриступы:\n");

        TA = new Long[N][2];
        BA = new Long[N][2];
        TAi = new LinkedHashMap<>();
        BAi = new LinkedHashMap<>();
        isTA = false;
        isBA = false;
        ti = 0;
        bi = 0;

        Iterator iterator = RR.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<Long, Double> currRR = (Entry<Long, Double>) iterator.next();
            if (0.9*NR < currRR.getValue() && 1.1*NR > currRR.getValue()) {
                if (isTA) saveTA();
                if (isBA) saveBA();

                isTA=false;
                isBA=false;
            }

            if (currRR.getValue()<0.9*NR) {
                if (isBA) saveBA();

                isTA=true;
                TAi.put(currRR.getKey(), currRR.getValue());
            }

            if (currRR.getValue()>1.1*NR) {
                if (isTA) saveTA();

                isBA=true;
                BAi.put(currRR.getKey(), currRR.getValue());
            }
        }
        if (isBA) saveBA();
        if (isTA) saveTA();
    }

    static void saveTA() {
        long first = (long) TAi.keySet().toArray()[0];
        long last = (long) TAi.keySet().toArray()[TAi.size() - 1];
        long duration = last - first;

        if (duration > 2*60*1000) {
            TA[ti][0] = first;
            TA[ti][1] = last;
            System.out.println("Приступ тахикардии № " + ti+1 +". Длительность : "+ duration);
            ti++;
        }
        TAi.clear();
        isTA=false;
    }

    static void saveBA(){
        long first = (long) BAi.keySet().toArray()[0];
        long last = (long) BAi.keySet().toArray()[BAi.size() - 1];
        long duration = last - first;

        if (duration > 2*60*1000) {
            BA[ti][0] = first;
            BA[ti][1] = last;
            System.out.println("Приступ барикардии № " + bi+1 +". Длительность : "+ duration);
            bi++;
        }
        BAi.clear();
        isBA=false;
    }
}
