package com.shpt.core.ext

import java.io.UnsupportedEncodingException
import java.lang.reflect.Field
import java.util.*

/**
 * Created by poovarasanv on 27/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 27/3/17 at 10:43 AM
 */

object StringUtils {
	val MIN_BUCKET_NAME_LENGTH = 3
	val MAX_BUCKET_NAME_LENGTH = 63
	
	fun join(strings: Array<Any>, spliter: String): String {
		var i = 0
		val buffer = StringBuffer()
		for (s in strings) {
			if (i == 0) {
				buffer.append(s)
				i = 1
			} else {
				buffer.append(spliter + s)
			}
		}
		return buffer.toString()
	}
	
	fun join(strings: IntArray, spliter: String): String {
		var i = 0
		val buffer = StringBuffer()
		for (s in strings) {
			if (i == 0) {
				buffer.append(s)
				i = 1
			} else {
				buffer.append(spliter + s)
			}
		}
		return buffer.toString()
	}
	
	fun join(strings: ByteArray, spliter: String): String {
		var i = 0
		val buffer = StringBuffer()
		for (s in strings) {
			if (i == 0) {
				buffer.append(s)
				i = 1
			} else {
				buffer.append(spliter + s)
			}
		}
		return buffer.toString()
	}
	
	fun join(strings: List<String>, spliter: String): String {
		return join(strings, spliter)
	}
	
	fun isBlank(s: String?): Boolean {
		if (s == null)
			return true
		if (s.trim { it <= ' ' }.length == 0)
			return true
		return false
	}
	
	fun validateBucketName(bname: String?): String? {
		if (bname == null) {
			return null
		}
		
		if (bname.length < MIN_BUCKET_NAME_LENGTH || bname.length > MAX_BUCKET_NAME_LENGTH) {
			return null
		}
		
		var previous = '\u0000'
		
		for (i in 0..bname.length - 1) {
			val next = bname[i]
			
			if (next >= 'A' && next <= 'Z') {
				return null
			}
			
			if (next == ' ' || next == '\t' || next == '\r' || next == '\n') {
				return null
			}
			
			if (next == '.') {
				if (previous == '.') {
					return null
				}
				if (previous == '-') {
					return null
				}
			} else if (next == '-') {
				if (previous == '.') {
					return null
				}
			} else if (next < '0' || next > '9' && next < 'a'
				|| next > 'z') {
				return null
			}
			
			previous = next
		}
		if (previous == '.' || previous == '-') {
			return null
		}
		return bname
	}
	
	fun object2string(obj: Any): String {
		return object2string(0, obj, null)
	}
	
	private val clazzs = Arrays.asList(*arrayOf(String::class.java, Boolean::class.java, Int::class.java, Long::class.java, Double::class.java, Float::class.java, Short::class.java, Byte::class.java, Collection::class.java, Map::class.java, HashMap::class.java, ArrayList::class.java, HashSet::class.java, java.util.Date::class.java))
	
	private fun object2string(index: Int, obj: Any, fieldF: Field?): String {
		val value = StringBuffer()
		val prefixSb = StringBuffer()
		for (i in 0..index - 1 - 1) {
			prefixSb.append("       ")
		}
		var prefix = prefixSb.toString()
		if (fieldF != null)
			value.append("\n" + prefix + fieldF.name + "=" + obj.javaClass + "\n")
		else
			value.append("\n" + prefix + obj.javaClass + "\n")
		if (index != 0)
			prefixSb.append("       ")
		prefix = prefixSb.toString()
		val fields = obj.javaClass.declaredFields
		val valuesToAdd = HashMap<Field, Any>()
		for (i in fields.indices) {
			val field = fields[i]
			field.isAccessible = true
			var fieldValue: Any? = null
			try {
				fieldValue = field.get(obj)
			} catch (e1: IllegalArgumentException) {
				// TODO Auto-generated catch block
				e1.printStackTrace()
			} catch (e1: IllegalAccessException) {
				// TODO Auto-generated catch block
				e1.printStackTrace()
			}
			
			if (fieldValue != null) {
				if (clazzs.contains(fieldValue.javaClass)) {
					
					value.append(prefix + field.name + "=" + fieldValue.toString()
						+ "\n")
				} else if (fieldValue.javaClass.isEnum) {
					value.append(prefix + field.name + "=" + fieldValue.toString()
						+ "\n")
				} else {
					valuesToAdd.put(field, fieldValue)
				}
			} else if (fieldValue == null) {
				value.append(prefix + field.name + "=null" + "\n")
			}
		}
		for ((key, value1) in valuesToAdd) {
			value.append(object2string(index + 1, value1, key))
		}
		return value.toString()
	}
	
	fun getBytesUnchecked(string: String?, charsetName: String): ByteArray? {
		if (string == null) {
			return null
		}
		try {
			return string.toByteArray(charset(charsetName))
		} catch (e: UnsupportedEncodingException) {
			throw StringUtils.newIllegalStateException(charsetName, e)
		}
		
	}
	
	private fun newIllegalStateException(charsetName: String, e: UnsupportedEncodingException): IllegalStateException {
		return IllegalStateException(charsetName + ": " + e)
	}
	
}