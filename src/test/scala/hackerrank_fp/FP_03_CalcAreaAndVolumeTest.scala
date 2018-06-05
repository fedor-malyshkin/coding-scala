package hackerrank_fp

import org.scalatest.FlatSpec

class FP_03_CalcAreaAndVolumeTest extends FlatSpec {

    behavior of "FP_03_CalcAreaAndVolume"

    it should "f 1^1 (1)" in {
        assertResult(1.0)(FP_03_CalcAreaAndVolume.f(List(1), List(1), 1))
    }

    it should "f 1^1 + 2^2 (2)" in {
        assertResult(10)(FP_03_CalcAreaAndVolume.f(List(1, 2), List(1, 2), 2))
    }


    it should "f from site" in {
        assertResult(8256.0)(FP_03_CalcAreaAndVolume.f(List(1, 2, 3, 4, 5), List(6, 7, 8, 9, 10), 2))
    }

    it should "f from 1 to 4" in {
        assertResult(2435300.3)(FP_03_CalcAreaAndVolume.summation(FP_03_CalcAreaAndVolume.f, 1, 4, List(1, 2, 3, 4, 5), List(6, 7, 8, 9, 10)))
    }
}
