package org.openmrs.module.accounting.api.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.accounting.api.AccountingService;
import org.openmrs.module.accounting.api.db.AccountingDAO;
import org.openmrs.module.accounting.api.model.Account;
import org.openmrs.module.accounting.api.model.AccountBalance;
import org.openmrs.module.accounting.api.model.BalanceStatus;
import org.openmrs.module.accounting.api.model.AccountTransaction;
import org.openmrs.module.accounting.api.model.FiscalPeriod;
import org.openmrs.module.accounting.api.model.FiscalYear;
import org.openmrs.module.accounting.api.model.GeneralStatus;
import org.openmrs.module.accounting.api.model.IncomeReceipt;
import org.openmrs.module.accounting.api.model.IncomeReceiptItem;
import org.openmrs.module.accounting.api.model.TransactionStatus;
import org.openmrs.module.accounting.api.model.TransactionType;
import org.openmrs.module.accounting.api.utils.DateUtils;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * It is a default implementation of {@link AccountingService}.
 */
public class AccountingServiceImpl extends BaseOpenmrsService implements AccountingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private AccountingDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(AccountingDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the dao
	 */
	public AccountingDAO getDao() {
		return dao;
	}
	
	public Account saveAccount(Account acc) {
		if (acc.getId() == 0) {
			log.info("Create new account: " + acc.getName());
			acc.setCreatedDate(Calendar.getInstance().getTime());
			acc.setCreatedBy(Context.getAuthenticatedUser().getId());
		} else {
			log.info("update  account: " + acc.getName());
			acc.setUpdatedBy(Context.getAuthenticatedUser().getId());
			acc.setUpdatedDate(Calendar.getInstance().getTime());
			if (acc.isRetired()) {
				acc.setRetiredDate(Calendar.getInstance().getTime());
				acc.setRetiredBy(Context.getAuthenticatedUser().getId());
			}
		}
		return dao.saveAccount(acc);
	}
	
	public void deleteAccount(Account acc) {
		dao.deleteAccount(acc);
	}
	
	public Collection<Account> getAccounts(boolean includeDisabled) {
		return dao.getAccounts(includeDisabled);
	}
	
	public Account getAccount(int id) {
		return dao.getAccount(id);
	}
	
	public FiscalYear saveFiscalYear(FiscalYear fy) {
		if (fy.getId() == null) {
			fy.setCreatedDate(Calendar.getInstance().getTime());
			fy.setCreatedBy(Context.getAuthenticatedUser().getId());
		} else {
			fy.setUpdatedBy(Context.getAuthenticatedUser().getId());
			fy.setUpdatedDate(Calendar.getInstance().getTime());
		}
		
		return dao.saveFiscalYear(fy);
	}
	
	public FiscalYear getFiscalYear(int id) {
		return dao.getFiscalYear(id);
	}
	
	public FiscalPeriod saveFiscalPeriod(FiscalPeriod fp) {
		
		fp.setEndDate(DateUtils.getEnd(fp.getEndDate()));
		return dao.saveFiscalPeriod(fp);
	}
	
	public FiscalPeriod getFiscalPeriod(int id) {
		return dao.getFiscalPeriod(id);
	}
	
	public AccountBalance saveAccountBalance(AccountBalance ap) {
		return dao.saveAccountBalance(ap);
	}
	
	public AccountBalance getAccountPeriod(int id) {
		return dao.getAccountPeriod(id);
	}
	
	public Collection<Account> getListParrentAccount() {
		return dao.getListParrentAccount();
	}
	
	public Account getAccountByName(String name) {
		return dao.getAccountByName(name);
	}
	
	public FiscalYear getFiscalYearByName(String name) {
		return dao.getFiscalYearByName(name);
	}
	
	public Collection<FiscalYear> getListFiscalYear(GeneralStatus status) {
		return dao.getListFiscalYear(status);
	}
	
	public void deleteFiscalYear(FiscalYear fiscalYear) {
		dao.deleteFiscalYear(fiscalYear);
		;
	}
	
	public void deletePeriod(FiscalPeriod period) {
		dao.deleteFiscalPeriod(period);
	}
	
	@Override
	public void initModule() {
		log.debug("****************************************");
		log.debug("INIT ACCOUNTING MODULE");
		log.debug("****************************************");
		//		Integer rootServiceConceptId = Integer.valueOf(Context.getAdministrationService().getGlobalProperty(
		//		    BillingConstants.GLOBAL_PROPRETY_SERVICE_CONCEPT));
		//		Concept rootServiceconcept = Context.getConceptService().getConcept(rootServiceConceptId);
		//		Collection<ConceptAnswer> answers = rootServiceconcept.getAnswers();
		//		log.debug(answers);
		//		
		//		for (ConceptAnswer ca : answers) {
		//			log.debug(ca.getAnswerConcept().getName().getName());
		//			
		//		}
		
	}
	
	@Override
	public IncomeReceipt saveIncomeReceipt(IncomeReceipt incomeReceipt) {
		if (incomeReceipt.getId() == null) {
			incomeReceipt.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setCreatedDate(Calendar.getInstance().getTime());
		} else {
			incomeReceipt.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceipt.setUpdatedDate(Calendar.getInstance().getTime());
		}
		return dao.saveIncomeReceipt(incomeReceipt);
	}
	
	@Override
	public IncomeReceipt getIncomeReceipt(Integer id) {
		return dao.getIncomeReceipt(id);
	}
	
	@Override
	public List<IncomeReceipt> getListIncomeReceipt(boolean includeVoided) {
		return dao.getListIncomeReceipt(includeVoided);
	}
	
	@Override
	public List<IncomeReceipt> getListIncomeReceiptByDate(String startDate, String endDate, boolean includeVoided) {
		return dao.getListIncomeReceiptByDate(startDate, endDate, includeVoided);
	}
	
	@Override
	public void delete(IncomeReceipt incomeReceipt) {
		dao.delete(incomeReceipt);
	}
	
	@Override
	public IncomeReceiptItem saveIncomeReceiptItem(IncomeReceiptItem incomeReceiptItem) throws Exception {
		IncomeReceiptItem rIncomeReceiptItem;
		log.debug("Save income reeceipt item: " + incomeReceiptItem);
		if (incomeReceiptItem.getId() == null) {
			/** New Receipt **/
			incomeReceiptItem.setCreatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setCreatedDate(Calendar.getInstance().getTime());
			
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/** Add Account Transaction **/
			addAccountTransaction(rIncomeReceiptItem, TransactionType.CREDIT);
			
			/**
			 * Update Account Balance
			 */
			updateAccountBalance(incomeReceiptItem.getAccount(), incomeReceiptItem.getReceipt().getReceiptDate(),
			    incomeReceiptItem.getAmount());
			
		} else {
			/** Update Receipt **/
			incomeReceiptItem.setUpdatedBy(Context.getAuthenticatedUser().getId());
			incomeReceiptItem.setUpdatedDate(Calendar.getInstance().getTime());
			
			/**
			 * When updating receipt item, there are 2 cases: Voided or Update the amount
			 * If Voided => cancelAccountTransaction for old receipt item
			 * Else If update amount =>  cancelAccountTransaction for old receipt then addAccountTransaction for new receipt amount
			 */
			
			IncomeReceiptItem oldReceipt = dao.getIncomeReceiptItem(incomeReceiptItem.getId());
			
			if (incomeReceiptItem.isVoided()) {
				
				/** Cancel Account Transaction for old receipt **/
				cancelAccountTransaction(oldReceipt);
				
			}else {
				
				/** Check if receipt amount were updated **/
				if (!oldReceipt.getAccount().equals(incomeReceiptItem.getAccount())) {
					
					/** Cancel Account Transaction for old receipt **/
					cancelAccountTransaction(oldReceipt);
					
					/** Add  transaction for new receipt amount value **/
				
					addAccountTransaction(incomeReceiptItem, TransactionType.CREDIT);
				}
			} 
			
			/** Update new receipt **/
			rIncomeReceiptItem = dao.saveIncomeReceiptItem(incomeReceiptItem);
			
			/** Update Account Balance **/
			updateAccountBalance(incomeReceiptItem.getAccount(), incomeReceiptItem.getReceipt().getReceiptDate(),
			    incomeReceiptItem.getAmount());
			
		}
		return rIncomeReceiptItem;
	}
	
	public void cancelAccountTransaction(IncomeReceiptItem receiptItem) {
		
		AccountTransaction oldTxn = dao.getAccountTxn(receiptItem.getTxnNumber());
		
		BigDecimal newBalance = oldTxn.getBalance().subtract(receiptItem.getAmount());
		
		AccountTransaction cancelTxn = new AccountTransaction();
		cancelTxn.setAccount(oldTxn.getAccount());
		cancelTxn.setBalance(newBalance);
		cancelTxn.setDebit(receiptItem.getAmount());
		cancelTxn.setCancelForTxn(oldTxn.getTxnNumber());
		cancelTxn.setTxnNumber(UUID.randomUUID().toString());
		cancelTxn.setBaseTxnNumber(oldTxn.getBaseTxnNumber());
		cancelTxn.setReferenceTxn(oldTxn.getTxnNumber());
		cancelTxn.setType(TransactionType.DEBIT);
		cancelTxn.setTxnStatus(TransactionStatus.CANCELED);
		cancelTxn.setCreatedDate(receiptItem.getCreatedDate());
		cancelTxn.setCreatedBy(receiptItem.getCreatedBy());
		dao.saveAccountTransaction(cancelTxn);
		
	}
	
	@Override
	public IncomeReceiptItem getIncomeReceiptItem(Integer id) {
		return dao.getIncomeReceiptItem(id);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItem(boolean includeVoided) {
		return dao.getListIncomeReceiptItem(includeVoided);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItemByDate(String startDate, String endDate) {
		return dao.getListIncomeReceiptItemByDate(startDate, endDate);
	}
	
	@Override
	public void delete(IncomeReceiptItem incomeReceiptItem) {
		dao.delete(incomeReceiptItem);
	}
	
	@Override
	public List<IncomeReceiptItem> getListIncomeReceiptItemByAccount(Account acc) {
		return dao.getListIncomeReceiptItemByAccount(acc);
	}
	
	@Override
	public List<AccountBalance> findAccountBalance(Integer fiscalPeriodId) {
		FiscalPeriod period = dao.getFiscalPeriod(fiscalPeriodId);
		if (period == null) {
			return null;
		} else {
			return dao.findAccountPeriods(period);
		}
	}
	
	@Override
	public void updateAccountLedgerBalance(Account account, Date receiptDate, BigDecimal updateAmount) throws Exception {
		
		FiscalPeriod period = dao.getPeriodByDate(receiptDate);
		if (period == null) {
			throw new Exception("Can not find  Period with Receipt Date: " + receiptDate.toString());
		}
		
		AccountBalance accPeriod = dao.getAccountPeriod(account, period);
		if (accPeriod == null) {
			throw new Exception("Can not find Account Period with Receipt Date: " + receiptDate.toString()
			        + " and Account: " + account.getName());
		}
		
		accPeriod.setLedgerBalance(accPeriod.getLedgerBalance().add(updateAmount));
		accPeriod.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accPeriod.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accPeriod);
	}
	
	@Override
	public AccountBalance findAccountPeriod(Account account, Date date) {
		return dao.findAccountPeriod(account, date);
	}
	
	@Override
	public void updateAccountAvailableBalance(Account account, Date receiptDate, BigDecimal amount) throws Exception {
		AccountBalance accPeriod = findAccountPeriod(account, receiptDate);
		if (accPeriod == null) {
			throw new Exception("Can not find Account Period with Account:" + account.getName() + " and Receipt Date: "
			        + receiptDate.toString());
		}
		
		accPeriod.setAvailableBalance(accPeriod.getLedgerBalance().add(amount));
		accPeriod.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accPeriod.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accPeriod);
	}
	
	@Override
	public void updateAccountBalance(Account account, Date receiptDate, BigDecimal amount) throws Exception {
		AccountBalance accPeriod = findAccountPeriod(account, receiptDate);
		if (accPeriod == null) {
			throw new Exception("Can not find Account Period with Account:" + account.getName() + " and Receipt Date: "
			        + receiptDate.toString());
		}
		
		accPeriod.setAvailableBalance(accPeriod.getLedgerBalance().add(amount));
		accPeriod.setLedgerBalance(accPeriod.getLedgerBalance().add(amount));
		accPeriod.setUpdatedBy(Context.getAuthenticatedUser().getId());
		accPeriod.setUpdatedDate(Calendar.getInstance().getTime());
		
		saveAccountBalance(accPeriod);
	}
	
	@Override
	public List<AccountBalance> listActiveAccountBalance() {
		return dao.listAccountBalance(BalanceStatus.ACTIVE);
	}
	
	public void addAccountTransaction(IncomeReceiptItem receipt, TransactionType txnType) {
		
		AccountTransaction oldTxn = dao.getLatestTransaction(receipt.getAccount());
		BigDecimal newBalance = receipt.getAmount();
		if (oldTxn != null) {
			if (txnType.equals(TransactionType.CREDIT)) {
				newBalance = newBalance.add(oldTxn.getBalance());
			} else if (txnType.equals(TransactionType.DEBIT)) {
				newBalance = oldTxn.getBalance().subtract(newBalance);
			}
		}
		
		AccountTransaction newTxn = new AccountTransaction();
		newTxn.setAccount(receipt.getAccount());
		newTxn.setTxnNumber(UUID.randomUUID().toString());
		newTxn.setBaseTxnNumber(newTxn.getTxnNumber());
		newTxn.setReferenceTxn(oldTxn.getTxnNumber());
		newTxn.setCreatedBy(receipt.getCreatedBy());
		newTxn.setCreatedDate(receipt.getCreatedDate());
		newTxn.setCredit(receipt.getAmount());
		newTxn.setTransctionDate(receipt.getReceipt().getReceiptDate());
		newTxn.setTxnStatus(TransactionStatus.OPEN);
		newTxn.setBalance(newBalance);
		newTxn.setType(txnType);
		
		dao.saveAccountTransaction(newTxn);
	}
}
