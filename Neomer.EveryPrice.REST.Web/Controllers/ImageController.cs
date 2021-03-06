﻿using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Neomer.EveryPrice.SDK.Web.Http;

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
    }
}