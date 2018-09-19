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
        public List<Product> Get(Guid shopUid)
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

            var products = ProductManager.Instance.GetProductsByShop(shop) as List<Product>;
            return products;
        }

        public Product Post(Guid uid, [FromBody] ProductEditModel productEditModel)
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

            return product as Product;
        }

        public Product Put(Guid shopUid, [FromBody] ProductEditModel productEditModel)
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
                CreationDate = DateTime.Now,
                Creator = user,
                Group = null,
                Shop = shop
            };

            productEditModel.ToProduct(ref product);
            ProductManager.Instance.SaveIsolate(product);

            return product as Product;
        }
    }
}
