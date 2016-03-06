package com.radiofc;


import java.util.ArrayList;
import java.util.List;

public class Permuter
{
    private List<List<Integer>> permutationsList;
    public Permuter(int[] a, int k) {
        permutationsList = new ArrayList<>();
        permute(a, k);
    }

    public void permute(int[] a, int k)
    {
        if (k == a.length) {
            List<Integer> destinations = new ArrayList<>();
            for (int i = 0; i < a.length; i++) {
                System.out.print(a[i]);
                destinations.add(a[i]);
            }
            permutationsList.add(destinations);
            System.out.println();
        }
        else
        {
            for (int i = k; i < a.length; i++)
            {
                int temp = a[k];
                a[k] = a[i];
                a[i] = temp;

                permute(a, k + 1);

                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }


    public List<List<Integer>> getPermutationsList() {
        return permutationsList;
    }
}