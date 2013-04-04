import org.junit.Assert;
import org.bukkit.ChatColor;
import org.junit.Test;

import runner.util.ChatUtil;

public class TestCase1 {

	@Test
	public void testPrintHelloWorld2() {

		Assert.assertEquals(ChatUtil.secToString(101), ChatColor.YELLOW + "1 ¤À 41 ¬í" + ChatColor.RESET);

	}

}