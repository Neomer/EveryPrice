using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class ImageController : ApiController
    {
        public ImageViewModel Get([FromUri] Guid id)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            return new ImageViewModel(BlobManager.Instance.Get(id) as IBlob);
        }
    }
}