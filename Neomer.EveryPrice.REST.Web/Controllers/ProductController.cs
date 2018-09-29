using Neomer.EveryPrice.REST.Web.Models;
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
    public class ProductController : ApiController
    {
        public List<ProductViewModel> Get(Guid shopUid)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shop = ShopManager.Instance.Get(shopUid) as IShop;
            if (shop == null)
            {
                return null;
            }

            var products = ProductManager.Instance.GetProductsByShop(shop);
            if (products == null)
            {
                return null;
            }

            return products
                .Select(_ => new ProductViewModel(_))
                .ToList<ProductViewModel>();
        }

        public ProductViewModel Post(Guid uid, [FromBody] ProductEditModel productEditModel)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var product = ProductManager.Instance.Get(uid) as IProduct;
            if (product == null)
            {
                return null;
            }
            productEditModel.ToProduct(ref product);
            ProductManager.Instance.SaveIsolate(product);

            return new ProductViewModel(product);
        }

        public ProductViewModel Put(Guid shopUid, [FromBody] ProductEditModel productEditModel)
        {
            var user = SecurityManager.Instance.GetUserByToken(Request.Headers);
            if (user == null)
            {
                return null;
            }
            var shop = ShopManager.Instance.Get(shopUid) as IShop;
            if (shop == null)
            {
                return null;
            }
            IProduct product = new Product()
            {
                CreationDate = DateTime.UtcNow,
                Creator = user,
                Group = null,
                Shop = shop
            };

            productEditModel.ToProduct(ref product);
            ProductManager.Instance.SaveIsolate(product);

            return new ProductViewModel(product);
        }
    }
}
