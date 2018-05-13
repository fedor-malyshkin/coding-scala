package bitworks.test_case

import hackerrank_fp.FP_01_UpdateList
import org.scalatest.FlatSpec

class FP_01_UpdateListTest extends FlatSpec {

	behavior of "FP_01_UpdateList"

	it should "updateList" in {
		val in = List.apply(2, -4, 3, -1, 23, -4, -54)
		val out = FP_01_UpdateList.f(in)

		assert(out.equals(List.apply(
			2,
			4,
			3,
			1,
			23,
			4,
			54))
		)

	}
}
