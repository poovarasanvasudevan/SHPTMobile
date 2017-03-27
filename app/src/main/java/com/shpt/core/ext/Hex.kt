package com.shpt.core.ext

import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Created by poovarasanv on 27/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 27/3/17 at 10:42 AM
 */

class Hex {
	
	val charsetName: String
	
	constructor() {
		// use default encoding
		this.charsetName = DEFAULT_CHARSET_NAME
	}
	
	constructor(csName: String) {
		this.charsetName = csName
	}
	
	@Throws(DecoderException::class)
	fun decode(array: ByteArray): ByteArray {
		try {
			return decodeHex(String(array, Charset.forName(charsetName)).toCharArray())
		} catch (e: UnsupportedEncodingException) {
			throw DecoderException(e.message!!, e)
		}
		
	}
	
	@Throws(DecoderException::class)
	fun decode(`object`: Any): Any {
		try {
			val charArray = if (`object` is String) (`object` as String).toCharArray() else `object` as CharArray
			return decodeHex(charArray)
		} catch (e: ClassCastException) {
			throw DecoderException(e.message!!, e)
		}
		
	}
	
	fun encode(array: ByteArray): ByteArray? {
		return StringUtils.getBytesUnchecked(encodeHexString(array), charsetName)
	}
	
	@Throws(EncoderException::class)
	fun encode(`object`: Any): Any {
		try {
			val byteArray = if (`object` is String) (`object` as String).toByteArray(charset(charsetName)) else `object` as ByteArray
			return encodeHex(byteArray)
		} catch (e: ClassCastException) {
			throw EncoderException(e.message!!, e)
		} catch (e: UnsupportedEncodingException) {
			throw EncoderException(e.message!!, e)
		}
		
	}
	
	public override fun toString(): String {
		return super.toString() + "[charsetName=" + this.charsetName + "]"
	}
	
	
	internal class DecoderException : Exception {
		constructor() : super() {}
		constructor(message: String) : super(message) {}
		constructor(message: String, cause: Throwable) : super(message, cause) {}
		constructor(cause: Throwable) : super(cause) {}
		
		companion object {
			private val serialVersionUID = 1L
		}
	}
	
	internal class EncoderException : Exception {
		constructor() : super() {}
		constructor(message: String) : super(message) {}
		constructor(message: String, cause: Throwable) : super(message, cause) {}
		constructor(cause: Throwable) : super(cause) {}
		
		companion object {
			private val serialVersionUID = 1L
		}
	}
	
	companion object {
		val DEFAULT_CHARSET_NAME = "UTF-8"
		private val DIGITS_LOWER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
		private val DIGITS_UPPER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
		
		@Throws(DecoderException::class)
		fun decodeHex(data: CharArray): ByteArray {
			val len = data.size
			
			if ((len and 0x01) != 0) {
				throw DecoderException("Odd number of characters.")
			}
			
			val out = ByteArray(len shr 1)
			
			// two characters form the hex value.
			var i = 0
			var j = 0
			while (j < len) {
				var f = toDigit(data[j], j) shl 4
				j++
				f = f or toDigit(data[j], j)
				j++
				out[i] = (f and 0xFF).toByte()
				i++
			}
			
			return out
		}
		
		@JvmOverloads fun encodeHex(data: ByteArray, toLowerCase: Boolean = true): CharArray {
			return encodeHex(data, if (toLowerCase) DIGITS_LOWER else DIGITS_UPPER)
		}
		
		protected fun encodeHex(data: ByteArray, toDigits: CharArray): CharArray {
			val l = data.size
			val out = CharArray(l shl 1)
			// two characters form the hex value.
			var i = 0
			var j = 0
			while (i < l) {
				out[j++] = toDigits[(0xF0 and data[i].toInt()).ushr(4)]
				out[j++] = toDigits[0x0F and data[i].toInt()]
				i++
			}
			return out
		}
		
		fun encodeHexString(data: ByteArray): String {
			return String(encodeHex(data))
		}
		
		@Throws(DecoderException::class)
		protected fun toDigit(ch: Char, index: Int): Int {
			val digit = Character.digit(ch, 16)
			if (digit == -1) {
				throw DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index)
			}
			return digit
		}
	}
}