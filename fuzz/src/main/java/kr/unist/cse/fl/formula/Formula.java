package kr.unist.cse.fl.formula;

import kr.unist.cse.fl.Statistics;
import scala.Int;

import java.util.List;

public interface Formula {
    void compute(Statistics db);
}
