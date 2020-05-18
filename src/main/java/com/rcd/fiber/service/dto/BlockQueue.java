package com.rcd.fiber.service.dto;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 16:39 2020/5/17
 * @Modify By:
 */
public class BlockQueue<T> {
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();

    private int defaultSize;
    private Queue<T> q ;
    private List<T> outList;
    public BlockQueue(Queue<T>q,List<T>outList,int size)
    {
        this.q = q;
        this.outList = outList;
        this.defaultSize = size;
    }

    public List<T> getOutList() {
        return outList;
    }

    public void put(T data)
    {
        try {
            lock.lock();
            while (q.size()==defaultSize)
            {
                System.out.println("队列满了");
                c1.await();
            }

            q.offer(data);
            System.out.println(String.format("Thread :%s, put data %d",Thread.currentThread().getName(),data));
            c2.signalAll();

        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public T  get()
    {
        T t = null;
        try {
            lock.lock();
            if (q.size()==0)
            {
                System.out.println("队列为空");
                c2.await();
            }
            t = q.poll();
            System.out.println(String.format("data: %d out",t));
            outList.add(t);
            c1.signalAll();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }finally {
            lock.unlock();
            return t;
        }
    }
}
