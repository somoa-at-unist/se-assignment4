package kr.unist.cse.fl;

import java.util.List;

public interface Statistics {
    /**
     *
     * @return a list of executed elements
     */
    List<Elem> coveredElems();

    int getFailed(Elem elem);

    int totalFailed();

    int getPassed(Elem elem);

    int totalPassed();
}
