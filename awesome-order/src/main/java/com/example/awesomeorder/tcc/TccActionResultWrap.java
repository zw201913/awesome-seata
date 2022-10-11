package com.example.awesomeorder.tcc;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouwei
 * @className TccActionResultWrap
 * @date: 2022/10/11 13:00
 * @description:
 */
public class TccActionResultWrap {

	private static final Map<String, String> prepareResult = new ConcurrentHashMap<>();

	private static final Map<String, String> commitResult = new ConcurrentHashMap<>();

	private static final Map<String, String> rollbackResult = new ConcurrentHashMap<>();

	public static void prepareSuccess(String xid) {
		prepareResult.put(xid, "success");
	}

	public static void commitSuccess(String xid) {
		commitResult.put(xid, "success");
	}

	public static void rollbackSuccess(String xid) {
		rollbackResult.put(xid, "success");
	}

	public static void removePrepareResult(String xid) {
		prepareResult.remove(xid);
	}

	public static void removeCommitResult(String xid) {
		commitResult.remove(xid);
	}

	public static void removeRollbackResult(String xid) {
		rollbackResult.remove(xid);
	}

	public static String prepareResult(String xid) {
		return prepareResult.get(xid);
	}

	public static String commitResult(String xid) {
		return commitResult.get(xid);
	}

	public static String rollbackResult(String xid) {
		return rollbackResult.get(xid);
	}

	public static boolean hasPrepareResult(String xid) {
		return StringUtils.isNotBlank(prepareResult(xid));
	}

	public static boolean hasCommitResult(String xid) {
		return StringUtils.isNotBlank(commitResult.get(xid));
	}

	public static boolean hasRollbackResult(String xid) {
		return StringUtils.isNotBlank(rollbackResult.get(xid));
	}
}
