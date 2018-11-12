using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
	[Serializable]
	public class ImageEditModel
	{

		public Guid Uid;

		public String DataEncoded;

		public void ToImage(ref IBlob imageModel)
		{
			if (imageModel == null)
			{
				return;
			}
			imageModel.Data = BlobHelper.Decode(DataEncoded);
		}
	}
}