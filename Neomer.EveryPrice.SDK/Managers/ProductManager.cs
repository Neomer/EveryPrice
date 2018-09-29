using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Models;
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

        public override void SaveIsolate(IEntity entity)
        {
            var product = entity as IProduct;
            if (product == null)
            {
                throw new UnsupportedEntityException();
            }
            using (var tr = NHibernateHelper.Instance.CurrentSession.BeginTransaction())
            {
                base.Save(product);
                if (product.Prices != null)
                {
                    foreach (var price in product.Prices)
                    {
                        PriceManager.Instance.Save(price);
                    }
                }
                try
                {
                    tr.Commit();
                }
                catch (Exception ex)
                {
                    tr.Rollback();
                    throw ex;
                }
            }
        }

        public IList<IProduct> GetProductsByShop(IShop shop)
        {
            if (shop == null)
            {
                return null;
            }
            var query = NHibernateHelper.Instance.CurrentSession
                .CreateSQLQuery(String.Format(@"
                    SELECT prod.*, pr.* FROM 
	                    [EveryPrice].[dbo].[Products] prod
                    CROSS APPLY (
	                    SELECT  TOP 1 *
	                    FROM [EveryPrice].[dbo].[Prices] pr
	                    WHERE pr.ProductUid = prod.Uid
	                    ORDER BY pr.CreationDate DESC
                    ) pr
                    WHERE [prod].[ShopUid]='{0}'", shop.Uid))
                .AddEntity(typeof(Product));

            return NHibernateHelper.Instance.CurrentSession
                .CreateCriteria<IProduct>()
                .Add(Expression.Eq("Shop", shop))
                .List<IProduct>();
        }
    }
}
