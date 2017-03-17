package dbModels;

import java.math.BigDecimal;
import java.util.List;

public interface AccountInfo {
	public List<Account> getAccountInfo(int idLogin);
	public boolean putMoney(int idLogin, BigDecimal amount);
	public boolean removeMoney(int idLogin, BigDecimal amount);
	//public Account fillAccount(int idLogin, BigDecimal amount, BigDecimal finalBalance);
}
