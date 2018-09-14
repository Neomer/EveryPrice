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

            NHibernateHelper.Instance.OpenSession(appPath + @"\Nhibernate.cfg.xml");

            {
                var transactionUid = NHibernateHelper.Instance.BeginTransaction();
                
                var adminUser = new User();
                adminUser.Username = "Admin";
                UserManager.Instance.RegisterUser(adminUser);

                NHibernateHelper.Instance.CommitTransaction(transactionUid);

                var profile = UserProfileManager.Instance.Get(adminUser.Uid);
            }

            NHibernateHelper.Instance.CloseSession();
        }
    }
}
