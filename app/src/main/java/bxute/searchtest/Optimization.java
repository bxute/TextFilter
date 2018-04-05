package bxute.searchtest;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Ankit on 3/27/2018.
 */

public class Optimization {

    private static IndexTreeBuilder indexTreeBuilder;
    private static HashMap<String, IndexNode> tree;

    public static void prepareNode(Context context){

        String s = context.getResources().getString(R.string.words);
        String[] strings = s.split(" ");
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(strings));

        if (words != null) {
            indexTreeBuilder = new IndexTreeBuilder(words);
        }
        tree = indexTreeBuilder.buildTree();

    }

}
