package com.company;

import java.math.BigInteger;
import java.util.*;

public class Main {

    final static int MAX_N=10;
    final static int SHOW_PERMS=1;
    final static ArrayList<ArrayList<Integer>> ANS_LIST=new ArrayList<>();
    final static ArrayList<Integer> realAns=new ArrayList<>();
    static int total_count=0;

    public static void main(String[] args) {

        AddRealAns();


        WayMonteCarlo();
//        WayNewCalculate();
//        WaySmartGenerate();

    }

    static void AddRealAns()
    {
        realAns.add(0);
        realAns.add(1);
        realAns.add(1);
        realAns.add(2);
        realAns.add(3);
        realAns.add(9);
        realAns.add(20);
        realAns.add(77);
        realAns.add(204);
        realAns.add(907);
        realAns.add(3244);
        realAns.add(17460);
        realAns.add(65793);
        realAns.add(401816);
    }

    static void WaySmartGenerate()
    {
        ArrayList<ArrayList<Integer>> parent_list=getAllPerms(MAX_N);
    }

    static ArrayList<ArrayList<Integer>> getAllPerms(int N)
    {
        ArrayList<ArrayList<Integer>> ans=new ArrayList<>();
        if (N==2)
        {
            ans.add(new ArrayList<Integer>());
            ans.get(0).add(2);
            ans.get(0).add(1);
            return ans;
        }

        ArrayList<ArrayList<Integer>> parent_list=getAllPerms(N-1);

        for (long i=0;i<parent_list.size();i++)
        {
            for (long j=0;j<N;j++)
            {
                parent_list.get((int)i).add((int)j,N);
//                if (validPerm())
            }
        }
        return ans;
    }

    static void WayNewCalculate()
    {
        ArrayList<Integer> a=new ArrayList<>();
        for (int i=0;i<MAX_N;i++)
        {
            a.add(i+1);
        }

        heapPermutation(a, a.size());

        ANS_LIST.sort(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                int i=0;
                while (o1.get(i)==o2.get(i))
                {
                    i++;
                }
                return o2.get(i)-o1.get(i);
            }
        });

        if (SHOW_PERMS==1)
        {
            for (int i=0;i<ANS_LIST.size();i++)
            {
                ArrayList<Integer> list=ANS_LIST.get(i);
                for (int j=0;j<list.size();j++)
                {
                    System.out.print(list.get(j)+" ");
                }
                System.out.print("\n");
            }
            System.out.println("Total valid: "+ANS_LIST.size());
        }
        else
        {
            System.out.println("Total valid: "+total_count);
        }
        System.out.println("Total possible: "+ factorial(MAX_N));
    }

    static void WayMonteCarlo()
    {
        int b[]=new int[10];
        for (int k=0;k<10;k++)
        {
            ArrayList<Integer> sample=new ArrayList<>();
            for (int i=0;i<MAX_N;i++)
            {
                sample.add(i+1);
            }
            int cnt=0;
            int total_trials=1000000;
            for (int i=0;i<total_trials;i++)
            {
                Collections.shuffle(sample);
                ArrayList<Integer> x=new ArrayList<>();
                for (int j=0;j<MAX_N;j++)
                {
                    x.add(j,sample.get(j));
                }

                if (validPerm(x)) cnt++;
            }
            double percent=(double) cnt/total_trials;
            double approx=percent*factorial(MAX_N);
            b[k]=(int)approx;
        }
        CalcMeanVar(b,10);
    }

    static void CalcMeanVar(int arr[],int n)
    {
        double sum = 0;
        double mean = 0;
        double variance = 0;
        double deviation = 0;

        int i = 0;

        for (i = 0; i < n; i++)
            sum = sum + arr[i];

        mean = sum / n;

        sum = 0;
        for (i = 0; i < n; i++) {
            sum = sum + Math.pow((arr[i] - mean), 2);
        }
        variance = sum / n;

        deviation = Math.sqrt(variance);

        System.out.printf("Mean of elements    : %.2f\n", mean);
        System.out.println("Real answer    : "+realAns.get(MAX_N));
        System.out.println("Difference    : "+(realAns.get(MAX_N)-Math.round(mean))+"\n");
//        System.out.printf("variance of elements: %.2f\n", variance);
        System.out.printf("Standard deviation  : %.2f\n", deviation);
        System.out.printf("Deviation Percent= %.2f\n\n",(double)deviation/mean*100);

        mean=Math.round(mean);
        System.out.printf("Error Percentage= %.2f\n",(double)Math.abs(mean-realAns.get(MAX_N))/realAns.get(MAX_N)*100);


    }
    static boolean validPerm(ArrayList<Integer> a)
    {
        TreeSet<Integer> possible=new TreeSet<>();
        Set<Integer> visited=new HashSet<>();
        possible.add(0);

        for (int i=0;i<a.size();i++)
        {
            int num=a.get(i);
            visited.add(num);
            Integer temp=possible.first();
            while (temp!=null && temp<=a.size())
            {
                if (!visited.contains(temp)&&temp!=0)
                {
                    return false;
                }
                possible.add(temp+num);
                temp=possible.higher(temp);
            }
        }
        return true;
    }

    // Prints the array
    static void addAns(ArrayList<Integer> a)
    {
        if (SHOW_PERMS==1)
        {
            ANS_LIST.add((ArrayList<Integer>) a.clone());
        }
        total_count++;
    }

    // Generating permutation using Heap Algorithm
    static void heapPermutation(ArrayList<Integer> a, int size)
    {

        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
        {
            if (validPerm(a))
            {
                addAns(a);
            }
        }


        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1);

            // if size is odd, swap 0th i.e (first) and
            // (size-1)th i.e (last) element
            if (size % 2 == 1) {
                int temp = a.get(0);
                a.set(0, a.get(size - 1));
                a.set(size - 1, temp);
            }

            // If size is even, swap ith
            // and (size-1)th i.e last element
            else {
                int temp = a.get(i);
                a.set(i, a.get(size - 1));
                a.set(size - 1, temp);
            }
        }
    }

    static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }
}
