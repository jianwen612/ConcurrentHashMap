package Testcases;

import MyConcurrentHashTable.MyConcurrentHashTable;

import java.util.ArrayList;

public class ThreadTestClear extends ThreadsTestRunableBase {
    public ThreadTestClear(int threadId, MyConcurrentHashTable<Integer, String> table, ArrayList<Integer> workload) {
        super(threadId, table, workload);
    }
    public ThreadTestClear(){
        super();
    }
    @Override
    public void run() {
        for (Integer integer : workload) {
            testClear();
        }

    }
}
