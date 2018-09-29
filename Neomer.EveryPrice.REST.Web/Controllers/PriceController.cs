﻿using Neomer.EveryPrice.REST.Web.Models;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web.Controllers
{
    public class PriceController : ApiController
    {
        public List<PriceViewModel> Get([FromUri] Guid productUid)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }

            var product = ProductManager.Instance.Get(productUid) as IProduct;
            if (product == null)
            {
                return null;
            }
            var priceList = PriceManager.Instance.GetPricesByProduct(product);
            if (priceList == null)
            {
                return null;
            }
            return priceList
                .Select(_ => new PriceViewModel(_))
                .ToList<PriceViewModel>();
        }

        public PriceViewModel Put(Guid productUid, [FromBody] PriceEditModel model)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }

            var product = ProductManager.Instance.Get(productUid) as IProduct;
            if (product == null)
            {
                return null;
            }
            IPrice price = new Price()
            {
                CreationDate = DateTime.UtcNow,
                Creator = user,
                Product = product
            };

            model.ToPrice(ref price);
            PriceManager.Instance.SaveIsolate(price);

            return new PriceViewModel(price);
        }
    }
}
