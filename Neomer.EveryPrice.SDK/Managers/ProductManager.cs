using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;
using NHibernate.Criterion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class ProductManager : BaseEntityManager<ProductManager, IProduct>
    {
        protected ProductManager()
        {

        }

        public override void SaveIsolate(ISession session, IEntity entity)
        {
            var product = entity as IProduct;
            if (product == null)
            {
                throw new UnsupportedEntityException();
            }
			base.SaveIsolate(session, product);
			
            if (product.Prices != null)
            {
                foreach (var price in product.Prices)
                {
					if (price.Uid == Guid.Empty)
					{
						price.Product = product;
						PriceManager.Instance.SaveIsolate(session, price);
					}
				}
            }
        }

        public IList<IProduct> GetProductsByShop(ISession session, IShop shop)
        {
            if (shop == null)
            {
                return null;
            }
            return session
				.CreateCriteria<IProduct>()
                .Add(Expression.Eq("Shop", shop))
                .AddOrder(Order.Asc("Name"))
                .List<IProduct>();
        }
    }
}
