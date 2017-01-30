package dbModels;

import java.math.BigDecimal;
import java.util.List;

public interface AccountInfo {
	public List<Account> getAccountInfo(String login);
	public boolean putMoney(String login, BigDecimal amount);
	public boolean removeMoney(String login, BigDecimal amount);
	public Account fillAccount(String login, BigDecimal amount, BigDecimal finalBalance);
}
