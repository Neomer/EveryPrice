using Neomer.EveryPrice.SDK.Helpers;
using Neomer.EveryPrice.SDK.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.Console
{
    class Program
    {
        static void Main(string[] args)
        {

            var location = System.Reflection.Assembly.GetExecutingAssembly().Location;
            var appPath = System.IO.Path.GetDirectoryName(location);

            NHibernateHelper.ApplyConfiguration(appPath + @"\Nhibernate.cfg.xml");
        }

        static void CreateGroups()
        {
            var transactionUid = NHibernateHelper.Instance.BeginTransaction();

            var g = new Group() { Name = "Алкоголь" };

            NHibernateHelper.Instance.CurrentSession.Save(g);
            NHibernateHelper.Instance.CurrentSession.Save(new Group() { Name = "Крепкий", Parent = g });
            NHibernateHelper.Instance.CurrentSession.Save(new Group() { Name = "Пиво", Parent = g });
            NHibernateHelper.Instance.CurrentSession.Save(new Group() { Name = "Вино", Parent = g });

            NHibernateHelper.Instance.CommitTransaction(transactionUid);
        }


        static void CreateAdmin()
        {
        }
    }
}
