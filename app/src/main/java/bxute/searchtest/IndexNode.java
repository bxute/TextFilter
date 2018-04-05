package bxute.searchtest;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ankit on 3/8/2018.
 */

public class IndexNode {

    @SerializedName("character")
    String character;
    @SerializedName("nextNodes")
    HashMap<String, IndexNode> nextIndexs;
    @SerializedName("listOfWords")
    ArrayList<Integer> indexOfWords;

    public IndexNode(String character) {
        this.character = character;
        indexOfWords = new ArrayList<>();
        nextIndexs = new HashMap<>();
    }

    public void addNode(IndexNode indexNode) {
        nextIndexs.put(indexNode.getCharacter(), indexNode);
    }

    public void addWord(int wordIndex) {
        indexOfWords.add(wordIndex);
    }

    public String getCharacter() {
        return character;
    }

    public ArrayList<Integer> getListOfWordIndexs() {
        return indexOfWords;
    }

    public boolean hasNodeWith(String currentCharacter) {
        return nextIndexs.get(currentCharacter) != null;
    }

    public IndexNode getNode(String currentCharacter) {
        return nextIndexs.get(currentCharacter);
    }

    @Override
    public String toString() {
        return "IndexNode{" +
                "character='" + character + '\'' +
                ", nextIndexs=" + getJson(nextIndexs) +
                ", listOfWords=" + indexOfWords +
                '}';
    }

    private String getJson(HashMap<String, IndexNode> node){

        JSONObject jsonObject= new JSONObject(node);
        return jsonObject.toString();

    }

}
