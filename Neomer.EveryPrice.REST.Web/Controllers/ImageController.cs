using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Neomer.EveryPrice.SDK.Web.Http;
using System.IO;
using Neomer.EveryPrice.SDK.Helpers;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class ImageController : BaseApiController
	{
        public ImageViewModel Get([FromUri] Guid id)
        {
            var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
            if (user == null)
            {
                return null;
            }
            var image = BlobManager.Instance.Get(CurrentSession, id) as IBlob;
            if (image == null)
            {
                throw new NotFoundException();
            }
            return new ImageViewModel(image);
        }

		public async System.Threading.Tasks.Task<ImagePreviewViewModel> PutAsync(Guid shopUid, [FromBody] ImageEditModel imageModel)
		{
			using (var contentStream = await Request.Content.ReadAsStreamAsync())
			{
				contentStream.Seek(0, SeekOrigin.Begin);
				using (StreamReader sr = new StreamReader(contentStream))
				{
					string rawContent = sr.ReadToEnd();
					Logger.Log.Debug(rawContent);
					// use raw content here
				}
			}
			var user = SecurityManager.Instance.GetUserByToken(CurrentSession, Request.Headers);
			if (user == null)
			{
				return null;
			}
			var shop = ShopManager.Instance.Get(CurrentSession, shopUid) as IShop;
			if (shop == null)
			{
				return null;
			}
			IBlob image = new Blob()
			{
				CreationDate = DateTime.UtcNow
			};

			imageModel.ToImage(ref image);
			BlobManager.Instance.SaveIsolate(CurrentSession, image);

			return new ImagePreviewViewModel(image);
		}
	}
}