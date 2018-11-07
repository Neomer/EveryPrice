using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Helpers
{
	public static class BlobHelper
	{
		public static String Encode(byte[] data)
		{
			return Convert.ToBase64String(data);
		}

		public static byte[] Decode(String encodedData)
		{
			return Convert.FromBase64String(encodedData);
		}
	}
}
